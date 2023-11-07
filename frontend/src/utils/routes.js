import {createBrowserRouter} from 'react-router-dom';
import App from '../App';

export function getRoutes() {
  return createBrowserRouter([
    {
      path: '/',
      element: <App/>,
    },
    {
      path: '/login',
      element: <>Login page</>
    }
  ]);
}
