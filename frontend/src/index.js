import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';
//import Login from './page/LoginPage/LoginPage';
import Home from './page/Homepage/Homebutton';
import Login from './page/LoginPage/LoginPage';
import Main from './page/MainPage/MainPage';
import Signup from './page/Signuppage/Signuppage'
import Navigation from './Nav'
import Com from './Route'
// import Rout from './Route'

ReactDOM.render(<Com />, document.getElementById('root'));
ReactDOM.render(<Navigation />, document.getElementById('nav'));
// ReactDOM.render(<Rout />, document.getElementById('route'));




