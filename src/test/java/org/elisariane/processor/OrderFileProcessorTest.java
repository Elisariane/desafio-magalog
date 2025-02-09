package org.elisariane.processor;

import org.elisariane.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFileProcessorTest {

    @Test
    void whenProcessingSingleFile_thenRetrunSuccess() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        Files.write(tempFile, "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308\n".getBytes());

        List<User> users = OrderFileProcessor.processFileOrDirectory(tempFile.toString());

        assertNotNull(users);
        assertEquals(1, users.size());

        User user = users.getFirst();
        assertEquals(70, user.getUserId());
        assertTrue(user.getOrdersMap().containsKey(753));

        Files.delete(tempFile);
    }

    @Test
    void whenProcessingDirectory_thenRetrunNoSuchFileException() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        Files.write(tempFile, "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308\n".getBytes());
        Path tempDirectory = Files.createTempDirectory("directory");

        assertThrows(NoSuchFileException.class, () ->  OrderFileProcessor.processFileOrDirectory(tempDirectory.toString()));

        Files.delete(tempFile);
        Files.delete(tempDirectory);
    }

    @Test
    void whenProcessingDirectoryWithFile_thenRetrunSuccess() throws IOException {

        Path tempDir = Files.createTempDirectory("batch_");
        Path tempFile = Files.createTempFile(tempDir, "file_", ".tmp");
        Files.write(tempFile, "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308\n".getBytes());

        List<User> users = OrderFileProcessor.processFileOrDirectory(tempDir.toString());

        assertNotNull(users);
        assertEquals(1, users.size());

        User user = users.getFirst();
        assertEquals(70, user.getUserId());
        assertTrue(user.getOrdersMap().containsKey(753));

        Files.delete(tempFile);
        Files.delete(tempDir);
    }

    @Test
    void whenProcessInvalidFile_thenReturnIOException() {
        assertThrows(IOException.class, () -> OrderFileProcessor.processFileOrDirectory("invalid-path"));
    }

}