package com.thehecklers.sburrestdemo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SburRestDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "droid")
	Droid createDroid()
	{
		return new Droid();
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

@RestController
@RequestMapping("/greeting")
class GreetingController
{
	private final Greeting greeting;
	public GreetingController(Greeting greeting)
	{
		this.greeting = greeting;
	}
	@GetMapping
	String getGreeting()
	{
		System.out.println(greeting.getName());
		return greeting.getName();
	}
	@GetMapping("/coffee")
	String getNameAndCoffee()
	{
		return greeting.getCoffee();
	}
}


@ConfigurationProperties(prefix = "greeting")
class Greeting
{
	private String name;
	private String coffee;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCoffee()
	{
		return coffee;
	}
	public void setCoffee(String coffee)
	{
		this.coffee = coffee;
	}
}

class Droid
{
	private String id, description;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
}

@RestController
@RequestMapping("/droid")
class DroidController
{
	private final Droid droid;
	public DroidController(Droid droid)
	{
		this.droid = droid;
	}
	@GetMapping
	Droid getDroid()
	{
		return droid;
	}
}