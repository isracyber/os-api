package com.israel.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.israel.os.domain.Cliente;
import com.israel.os.domain.Pessoa;
import com.israel.os.dtos.ClienteDTO;
import com.israel.os.repositories.ClienteRepository;
import com.israel.os.repositories.PessoaRepository;
import com.israel.os.resources.exceptions.DataIntegratyViolationException;
import com.israel.os.services.expections.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}

	
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/*
	 * cria um cliente
	 */
	public Cliente create (ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados");
		}
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	/*
	 * atualiza um cliente
	 */
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		
		Cliente oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados");
			
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	/*
	 * deleta um cliente pelo ID
	 */
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("Pessoa possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findbyCPF(objDTO.getCpf());
		 if(obj != null) {
			 return obj;
		 }
		 return null;
	}

	
	
}
