package com.magicstone.mina.core.service;

public interface IAcceptor extends IoService {

	public void bind(String host, int port);

}
