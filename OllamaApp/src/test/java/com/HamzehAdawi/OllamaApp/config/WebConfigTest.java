package com.HamzehAdawi.OllamaApp.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static org.mockito.Mockito.*;

class WebConfigTest {

    @Test
    void addResourceHandlers_registersImagesPath() {
        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);

        when(registry.addResourceHandler("/images/*")).thenReturn(registration);

        new WebConfig().addResourceHandlers(registry);

        verify(registry).addResourceHandler("/images/*");
        verify(registration).addResourceLocations("classpath:/static/images/");
    }
}
