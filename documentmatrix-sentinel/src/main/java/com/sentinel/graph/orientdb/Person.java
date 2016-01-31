package com.sentinel.graph.orientdb;

import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;

/**
 * 
 * @author Vipin Kumar
 *
 */
public interface Person {

	@Property("name")
	public void setName(String name);

	@Property("name")
	public String getName();

	@Property("age")
	public void setAge(int age);

	@Property("age")
	public int getAge();

	@Adjacency(label = "knows")
	public Iterable<Person> getKnowsPeople();

	@Adjacency(label = "knows")
	public void addKnowsPerson(Person person);
}
