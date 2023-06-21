package com.israel.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.israel.os.domain.Cliente;
import com.israel.os.domain.OS;
import com.israel.os.domain.Tecnico;
import com.israel.os.domain.enuns.Prioridade;
import com.israel.os.domain.enuns.Status;
import com.israel.os.repositories.ClienteRepository;
import com.israel.os.repositories.OSRepository;
import com.israel.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Israel Feitosa", "486.798.890-16", "(56)87777-8888");
		Tecnico t2 = new Tecnico(null, "Linux Torvalds", "687.984.820-68", "(56)92222-4444");
		Cliente c1 = new Cliente(null, "Betina Campos", "097.926.040-06", "(55)84444-5555");

		OS os1 = new OS(null, Prioridade.ALTA, "Trocar fonte do notebook", Status.ANDAMENTO, t1, c1);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
