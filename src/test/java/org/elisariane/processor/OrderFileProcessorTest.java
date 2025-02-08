package org.elisariane.processor;

import org.elisariane.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
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
    void whenProcessInvalidFile_thenReturnIOException() {
        assertThrows(IOException.class, () -> OrderFileProcessor.processFileOrDirectory("invalid-path"));
    }

}