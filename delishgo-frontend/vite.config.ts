import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000, 
    host: true, // Permite que Docker mapee el puerto correctamente
    proxy: {
      // Si corres "npm run dev" en tu PC, esto redirigirá al Gateway de Docker
      '/api/v1': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      }
    }
  }
})