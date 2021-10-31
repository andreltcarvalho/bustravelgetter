package com.fastguiche.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fastguiche.entities.Cidade;
import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.CidadeService;
import com.fastguiche.services.RodoviariaService;

@Configuration
@Profile ("dev")
public class DevConfig implements CommandLineRunner
{

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private RodoviariaService rodoviariaService;

    @Override
    public void run(String... args) throws Exception
    {
        final Cidade c1 = new Cidade(null, "São Paulo, SP - Tietê", "sao paulo, sp - tiete", 1, "cidade", null);
        final Rodoviaria r1 = new Rodoviaria(null, "São Paulo, SP - Tietê", "sao paulo, sp - tiete", "rodoviaria", c1);

        cidadeService.insert(c1);
        rodoviariaService.insert(r1);
    }

}
