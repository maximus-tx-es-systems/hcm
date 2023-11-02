import Dashboard from './components/dashboard/Dashboard';
import App from './App';
import AppNode from './components/appNode/AppNode';

const AppRouter = [
        {
            "path": "/",
            element: <Dashboard />
        },
        {
            "path": "/dashboard",
            element: <Dashboard />
        },
        {
            "path": "module/:moduleCode",
            element: <AppNode />,
            children: [
                {
                    "path": "status",
                    element: <AppNode/>
                }
            ]
        }];




/*//import { createBrowserRouter } from "react-router-dom";

const AppRouter = createBrowserRouter([
        {
            "path": "/",
            element: <Dashboard />
        }
    ]);
    */
export default AppRouter;