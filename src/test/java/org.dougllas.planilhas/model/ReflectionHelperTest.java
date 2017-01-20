package org.dougllas.planilhas.model;

import org.dougllas.planilhas.annotation.AnnotationConfig;
import org.dougllas.planilhas.reflection.ReflectionHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Criado por dougllas.sousa em 16/01/2017.
 */
public class ReflectionHelperTest {

    @Test
    public void deveExtrairAConfiguracaoDaClasse(){
        List<AnnotationConfig> configs = ReflectionHelper.extractConfigurationfromClass(ModelClass.class);
        Assert.assertNotNull(configs);
    }
}
