import {createBrowserRouter} from 'react-router-dom';
import App from '../App';
import Login from '../components/login';

export function getRoutes() {
  return createBrowserRouter([
    {
      path: '/',
      element: <App/>,
    },
    {
      path: '/login',
      element: <Login/>
    }
  ]);
}
