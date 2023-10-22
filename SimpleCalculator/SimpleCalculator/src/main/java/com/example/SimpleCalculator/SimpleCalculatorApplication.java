package com.example.SimpleCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SimpleCalculatorApplication
{
    public static void main(String[] args)
	{
        SpringApplication.run(SimpleCalculatorApplication.class, args);
    }

}


@RestController
@RequestMapping
class APIController
{

	// http://localhost:8080/x+y
    @GetMapping("/{x}+{y}")
    int add(@PathVariable int x, @PathVariable int y)
	{
        return x + y;
    }

	// http://localhost:8080/x-y
    @GetMapping("/{x}-{y}")
    int subtract(@PathVariable int x, @PathVariable int y)
	{
        return x - y;
    }

	// http://localhost:8080/xxy
    @GetMapping("/{x}x{y}")
    int multiply(@PathVariable int x, @PathVariable int y)
	{
        return x * y;
    }

	// http://localhost:8080/x/y
    @GetMapping("/{x}/{y}")
    int divide(@PathVariable int x, @PathVariable int y)
	{
        return x / y;
    }
}