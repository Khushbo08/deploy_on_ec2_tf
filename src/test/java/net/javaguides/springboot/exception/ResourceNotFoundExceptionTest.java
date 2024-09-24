package net.javaguides.springboot.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void ResourceNotFoundExceptionTest() {
        String errorMessage = "Test Resource Not Found Exception";
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
