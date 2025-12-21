package com.example.demo.service;

import com.example.demo.dto.ViaCepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Service
public class ViaCepService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL_VIACEP = "https://viacep.com.br/ws/{cep}/json/";
    @Autowired
    private UrlBasedViewResolver urlBasedViewResolver;

    public ViaCepResponse bucarEnderecoPorCep(String cep){
        try{
            String url = URL_VIACEP.replace("{cep}", cep);

            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);
            return response;
        }catch (Exception e){
            throw e;
        }
    }

}
