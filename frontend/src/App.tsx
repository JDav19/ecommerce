import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/layout/Layout';
import ProductCatalog from './components/products/ProductCatalog';
import Cart from './components/cart/Cart';
import OrderList from './components/orders/OrderList';
import Checkout from './components/orders/Checkout';
import InventoryPage from './pages/InventoryPage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<ProductCatalog />} />
          <Route path="cart" element={<Cart />} />
          <Route path="orders" element={<OrderList />} />
          <Route path="checkout" element={<Checkout />} />
          <Route path="inventory" element={<InventoryPage />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
