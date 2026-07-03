import { useEffect, useState } from 'react';
import axios from 'axios';
import './App.css';

// Interfaces para mapear tus modelos de Java
interface Producto { id?: number; nombre: string; precio: number; }
interface Cliente { id?: number; nombre: string; email: string; }
interface Restaurante { id?: number; nombre: string; direccion: string; }
interface Pedido { id?: number; descripcion: string; total: number; }

function App() {
  // Estados para almacenar la información de cada mscv
  const [productos, setProductos] = useState<Producto[]>([]);
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
  const [pedidos, setPedidos] = useState<Pedido[]>([]);

  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  // CORRECCIÓN: Apuntar directamente a los puertos expuestos de cada microservicio
  const URL_CLIENTES = 'http://localhost:8081/api/v1/clientes';
  const URL_PEDIDOS = 'http://localhost:8083/api/v1/pedidos';
  const URL_PRODUCTOS = 'http://localhost:8084/api/v1/productos';
  const URL_RESTAURANTES = 'http://localhost:8085/api/v1/restaurantes';

  useEffect(() => {
    const cargarTodo = async () => {
      try {
        setLoading(true);
        setError(null);

        // Hacemos todas las llamadas HTTP en paralelo directamente a cada puerto real
        const [resProd, resCli, resRest, resPed] = await Promise.all([
          axios.get(URL_PRODUCTOS),
          axios.get(URL_CLIENTES),
          axios.get(URL_RESTAURANTES),
          axios.get(URL_PEDIDOS)
        ]);

        setProductos(resProd.data);
        setClientes(resCli.data);
        setRestaurantes(resRest.data);
        setPedidos(resPed.data);

      } catch (err) {
        console.error("Error cargando los servicios independientes:", err);
        setError("Error de comunicación. Revisa que los microservicios de Docker (8081, 8083, 8084, 8085) estén arriba.");
      } finally {
        setLoading(false);
      }
    };

    cargarTodo();
  }, []);

  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif', maxWidth: '1200px', margin: '0 auto' }}>
      <h1>🍽️ DelishGo Central Dashboard</h1>
      <p style={{ color: '#888' }}>Monitoreo nativo en la nube de tus microservicios (Conexión Directa)</p>
      <hr />

      {loading && <p style={{ fontSize: '1.2em', fontWeight: 'bold' }}>Cargando datos de los clústeres...</p>}
      {error && <p style={{ color: 'red', fontWeight: 'bold' }}>⚠️ {error}</p>}

      {/* Grid responsivo para ordenar los 4 microservicios */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px', marginTop: '20px' }}>
        
        {/* 1. PRODUCTOS */}
        <div style={{ background: '#1e1e1e', padding: '15px', borderRadius: '8px', textAlign: 'left', color: '#fff' }}>
          <h3>🍎 Productos ({productos.length})</h3>
          <ul>
            {productos.map((p, i) => <li key={p.id || i}>{p.nombre} - ${p.precio}</li>)}
            {!loading && productos.length === 0 && <li>Sin registros en DB</li>}
          </ul>
        </div>

        {/* 2. CLIENTES */}
        <div style={{ background: '#1e1e1e', padding: '15px', borderRadius: '8px', textAlign: 'left', color: '#fff' }}>
          <h3>👥 Clientes ({clientes.length})</h3>
          <ul>
            {clientes.map((c, i) => <li key={c.id || i}>{c.nombre} <br/><small style={{color: '#aaa'}}>{c.email}</small></li>)}
            {!loading && clientes.length === 0 && <li>Sin registros en DB</li>}
          </ul>
        </div>

        {/* 3. RESTAURANTES */}
        <div style={{ background: '#1e1e1e', padding: '15px', borderRadius: '8px', textAlign: 'left', color: '#fff' }}>
          <h3>🏪 Restaurantes ({restaurantes.length})</h3>
          <ul>
            {restaurantes.map((r, i) => <li key={r.id || i}>{r.nombre}</li>)}
            {!loading && restaurantes.length === 0 && <li>Sin registros en DB</li>}
          </ul>
        </div>

        {/* 4. PEDIDOS */}
        <div style={{ background: '#1e1e1e', padding: '15px', borderRadius: '8px', textAlign: 'left', color: '#fff' }}>
          <h3>📦 Pedidos ({pedidos.length})</h3>
          <ul>
            {pedidos.map((ped, i) => <li key={ped.id || i}>Ped #{ped.id} - Total: ${ped.total}</li>)}
            {!loading && pedidos.length === 0 && <li>Sin registros en DB</li>}
          </ul>
        </div>

      </div>
    </div>
  );
}

export default App;