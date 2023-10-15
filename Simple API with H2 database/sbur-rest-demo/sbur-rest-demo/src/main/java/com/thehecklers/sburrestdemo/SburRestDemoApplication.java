package com.thehecklers.sburrestdemo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SburRestDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SburRestDemoApplication.class, args);
	}
}

@Entity
class Coffee
{
	@Id
	private String id;
	private String name;

	public Coffee () {}

	public  Coffee(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Coffee(String name)
	{
		this(UUID.randomUUID().toString(), name);
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}


@RestController
@RequestMapping("/coffees")
class RestApiDemoController
{
	private final CoffeeRepository coffeeRepository;

	public RestApiDemoController(CoffeeRepository coffeeRepository)
	{
		this.coffeeRepository = coffeeRepository;
		this.coffeeRepository.saveAll(List.of(new Coffee("Café Cereza"),
				new Coffee("Café Ganador"),
				new Coffee("Café Lareño"),
				new Coffee("Café Três Pontas")
		));
	}


	 // @RequestMapping(value = "/coffees", method = RequestMethod.GET) // you can replace this line by @GetMapping("/coffees")
	 @GetMapping
	Iterable<Coffee> getCoffees()
	{
		return coffeeRepository.findAll();
	}

	/**
	 * @RequestMapping has several specialized convenience annotations:
	 * • @GetMapping
	 * • @PostMapping
	 * • @PutMapping
	 * • @PatchMapping
	 * • @DeleteMapping
	 * */

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id)
	{
		return coffeeRepository.findById(id);
	}

	@PostMapping
	Coffee postCoffee(@RequestBody Coffee coffee)
	{
		return coffeeRepository.save(coffee);
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee)
	{
		return (coffeeRepository.existsById(id))
				? new ResponseEntity<>(coffeeRepository.save(coffee),
				HttpStatus.OK)
				: new ResponseEntity<>(coffeeRepository.save(coffee),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	void deleteCoffee(@PathVariable String id)
	{
		coffeeRepository.deleteById(id);
	}
}

interface CoffeeRepository extends CrudRepository<Coffee, String> {}

@Component
class DataLoader
{
	private final CoffeeRepository coffeeRepository;
	public DataLoader(CoffeeRepository coffeeRepository)
	{
		this.coffeeRepository = coffeeRepository;
	}
	@PostConstruct
	private void loadData()
	{
		coffeeRepository.saveAll(List.of(
				new Coffee("Café Cereza"),
				new Coffee("Café Ganador"),
				new Coffee("Café Lareño"),
				new Coffee("Café Três Pontas")
		));
	}
}
