import { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';

export default function Navbar() {
  const [cartCount, setCartCount] = useState(0);

  // Escucha el evento global 'cart-updated' que despachan Cart y Checkout
  useEffect(() => {
    const handler = (e: Event) => {
      const count = (e as CustomEvent<{ count: number }>).detail.count;
      setCartCount(count);
    };
    window.addEventListener('cart-updated', handler);
    return () => window.removeEventListener('cart-updated', handler);
  }, []);

  return (
    <nav className="bg-primary-700 text-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex items-center justify-between h-16">
        <span className="text-xl font-bold tracking-tight">Ecommerce</span>
        <div className="flex gap-6 items-center">

          <NavLink
            to="/"
            end
            className={({ isActive }) =>
              `text-sm font-medium transition-colors hover:text-primary-100 ${
                isActive ? 'underline underline-offset-4' : ''
              }`
            }
          >
            Productos
          </NavLink>

          {/* Carrito con badge de cantidad */}
          <NavLink
            to="/cart"
            className={({ isActive }) =>
              `relative text-sm font-medium transition-colors hover:text-primary-100 ${
                isActive ? 'underline underline-offset-4' : ''
              }`
            }
          >
            Carrito
            {cartCount > 0 && (
              <span className="absolute -top-2 -right-4 min-w-[18px] h-[18px] bg-red-500 text-white text-[10px] font-bold rounded-full flex items-center justify-center px-1">
                {cartCount > 99 ? '99+' : cartCount}
              </span>
            )}
          </NavLink>

          <NavLink
            to="/orders"
            className={({ isActive }) =>
              `text-sm font-medium transition-colors hover:text-primary-100 ${
                isActive ? 'underline underline-offset-4' : ''
              }`
            }
          >
            Pedidos
          </NavLink>

          <NavLink
            to="/inventory"
            className={({ isActive }) =>
              `text-sm font-medium transition-colors hover:text-primary-100 ${
                isActive ? 'underline underline-offset-4' : ''
              }`
            }
          >
            Inventario
          </NavLink>

        </div>
      </div>
    </nav>
  );
}
