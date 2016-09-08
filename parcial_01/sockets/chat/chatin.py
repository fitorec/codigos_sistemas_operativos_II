#!/usr/bin/python

import socket, sys

class Chatin:

	def conectar(self):
		self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.sock.connect(("127.0.0.1", 10))
		self.id = int(self.obtenerLinea())
		print "Soy el cliente:", self.id
		while(True):
			while(True):
				msgRecibido = self.obtenerLinea()
				if len(msgRecibido) == 0:
					break
				print msgRecibido
			msg = sys.stdin.readline()
			self.sock.send(msg.encode("utf-8"))
			if msg == "exit\n":
				print "ADIOS"
				return 0

	# Se encarga de recibir una linea del socket
	def obtenerLinea(self):
		linea = ''
		while True:
			c = self.sock.recv(1)
			if c == '\n':
				break
			linea += c
		return linea

cliente = Chatin()
cliente.conectar()
