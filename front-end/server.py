#!/usr/bin/env python3
import http.server
import socketserver
import os
import webbrowser
from threading import Timer

PORT = 3001

class MyHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=os.path.dirname(os.path.realpath(__file__)), **kwargs)
    
    def end_headers(self):
        self.send_header('Cache-Control', 'no-cache, no-store, must-revalidate')
        self.send_header('Pragma', 'no-cache')
        self.send_header('Expires', '0')
        super().end_headers()

def open_browser():
    webbrowser.open(f'http://localhost:{PORT}')

if __name__ == "__main__":
    with socketserver.TCPServer(("", PORT), MyHTTPRequestHandler) as httpd:
        print(f"🚀 Servidor rodando em http://localhost:{PORT}")
        print(f"📁 Servindo arquivos de: {os.getcwd()}")
        print("Pressione Ctrl+C para parar o servidor")
        
        # Abrir o navegador após 1 segundo
        Timer(1.0, open_browser).start()
        
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\n👋 Servidor parado!")
            httpd.shutdown()
