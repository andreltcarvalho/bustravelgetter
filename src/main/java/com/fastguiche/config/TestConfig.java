package com.fastguiche.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.RodoviariaService;

@Configuration
@Profile ("test")
public class TestConfig implements CommandLineRunner
{

    @Autowired
    private RodoviariaService rodoviariaService;

    @Override
    public void run(String... args) throws Exception
    {
        final Rodoviaria r1 = new Rodoviaria(null, "Vitória - Praça do Papa Bus Station", "vitoria-es-praça-do-papa");
        rodoviariaService.insert(r1);
    }

}
