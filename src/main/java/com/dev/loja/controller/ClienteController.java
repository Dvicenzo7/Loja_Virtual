package com.dev.loja.controller;


import com.dev.loja.entity.Cidade;
import com.dev.loja.entity.Cliente;
import com.dev.loja.repository.CidadeRepository;
import com.dev.loja.repository.ClienteRepository;
import com.dev.loja.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/cliente/cadastrar")
    public ModelAndView cadastrar(Cliente cliente){
        ModelAndView mv = new ModelAndView("cadastrar");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidade", cidadeRepository.findAll());
        return mv;
    }


    @PostMapping("/cliente/salvar")
    public ModelAndView salvar(Cliente cliente, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(cliente);
        }
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        clienteRepository.saveAndFlush(cliente);
        return cadastrar(new Cliente());
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editar(@PathVariable ("id")Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cadastrar(cliente.get());
    }

    @PostMapping("/finalizar/login")
    public String login(String username, String password, Model model) {
        // Verificando se o cliente existe e a senha está correta
        Optional<Cliente> cliente = clienteRepository.findByEmail(username);

        if (cliente.isPresent() && passwordEncoder.matches(password, cliente.get().getSenha())) {
            // Redireciona para a URL /carrinho após o login bem-sucedido
            return "redirect:/";
        } else {
            // Caso falhe, adiciona a mensagem de erro no model
            model.addAttribute("erroLogin", "Usuário ou senha incorretos. Tente novamente.");
            return "redirect:/cliente/cadastrar"; // Ou retornar para a página de login
        }
    }


}
