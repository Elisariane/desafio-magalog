package org.elisariane.processor;

import org.elisariane.aggregator.OrderAggregator;
import org.elisariane.dtos.OrderLine;
import org.elisariane.exceptions.UnparseableLineException;
import org.elisariane.models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OrderFileProcessor {

    private static final OrderLineParser orderLineParser = new OrderLineParser();
    private static final OrderAggregator orderAggregator = new OrderAggregator();

    public static List<User> processFileOrDirectory(String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        List<User> users = new ArrayList<>();

        if (Files.isDirectory(path)) {
            processDirectory(path, users);
        } else if (Files.isRegularFile(path)) {
            users.addAll(processFile(path.toFile()));
        } else {
            throw new FileNotFoundException("Caminho não encontrado ou inválido: {}" + pathStr);
        }
        return users;
    }

    private static void processDirectory(Path path, List<User> users) throws IOException {
        File directory = path.toFile();
        File[] files = directory.listFiles();
        if (files != null && Arrays.stream(files).findAny().isPresent()) {
            for (File file : files) {
                if (file.isFile()) {
                    users.addAll(processFile(file));
                }
            }
        } else {
            throw new NoSuchFileException("Arquivos dentro do diretório: {} não foram encontrados" + directory);
        }

    }

    private static List<User> processFile(File file) throws IOException {
        try (var lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                OrderLine orderLine = orderLineParser.parse(line);
                orderAggregator.aggregate(orderLine);
            });
        } catch (UnparseableLineException unparseableLineException) {
            throw new UnparseableLineException("Erro ao tentar converter linha: {}" + unparseableLineException.getMessage());
        }

        return orderAggregator.getAggregatedUsers();
    }
}
