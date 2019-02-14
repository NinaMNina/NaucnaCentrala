const ROOT_PATH = "/naucnaCentrala/rest/";
var app = angular.module('app', [ 'ui.router', 'ngStorage', 'angular-jwt' ]);

app.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/home");

    $stateProvider
	.state('home', {
        url: '/home',
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
    .state('zadaci', {
        url: '/zadaci/{token}',
        templateUrl : 'appParts/zadaci/zadaci.html',
        controller : 'zadaciController'
    })
  });
