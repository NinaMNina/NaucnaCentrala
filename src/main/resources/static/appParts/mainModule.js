const ROOT_PATH = "/naucnaCentrala/rest/";
var app = angular.module('app', [ 'ui.router', 'ngStorage', 'angular-jwt' ]);

app.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/home/{token}");

    $stateProvider
	.state('home', {
        url: '/home/{token}',
        templateUrl : 'appParts/home/home.html',
        controller : 'homeController'
    })
    .state('login', {
        url: '/login',
        templateUrl : 'appParts/login/login.html',
        controller : 'loginController'
    })
    .state('registracija', {
        url: '/registracija',
        templateUrl : 'appParts/registracija/registracija.html',
        controller : 'registracijaController'
    })
  });
