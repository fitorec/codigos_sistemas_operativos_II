#!/usr/bin/python

import socket, sys

class Chatin:
	id
	def conectar(self):
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.connect(("127.0.0.1", 10))
		linea = ''
		while True:
			c = s.recv(1)
			if c == '\n':
				break
			linea += c
		self.id = int(linea)
		print "Soy el cliente:", self.id
		while(True):
			msg = sys.stdin.readline()
			s.send(msg.encode("utf-8"))
			if msg == "exit\n":
				print "ADIOS"
				return 0

cliente = Chatin()
cliente.conectar()
