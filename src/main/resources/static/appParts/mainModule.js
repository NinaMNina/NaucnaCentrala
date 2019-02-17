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
    .state('prijaviRad', {
        url: '/prijaviRad',
        templateUrl : 'appParts/prijaviRad/prijaviRad.html',
        controller : 'prijaviRadController'
    })    
    .state('pregledPodataka', {
        url: '/pregledPodataka/{token}',
        templateUrl : 'appParts/pregledPodataka/pregledPodataka.html',
        controller : 'pregledPodatakaController'
    })
    .state('pregledPDFa', {
        url: '/pregledPDFa/{token}',
        templateUrl : 'appParts/pregledPDFa/pregledPDFa.html',
        controller : 'pregledPDFaController'
    })
    .state('uploadPDFa', {
        url: '/uploadPDFa/{token}',
        templateUrl : 'appParts/uploadPDFa/uploadPDFa.html',
        controller : 'uploadPDFaController'
    })
    .state('izborRecenzenata', {
        url: '/izborRecenzenata/{token}',
        templateUrl : 'appParts/izborRecenzenata/izborRecenzenata.html',
        controller : 'izborRecenzenataController'
    })
    .state('aktivnostRecenzenta', {
        url: '/aktivnostRecenzenta/{token}',
        templateUrl : 'appParts/aktivnostRecenzenta/aktivnostRecenzenta.html',
        controller : 'aktivnostRecenzentaController'
    })
    .state('pregledOcena', {
        url: '/pregledOcena/{token}',
        templateUrl : 'appParts/pregledOcena/pregledOcena.html',
        controller : 'pregledOcenaController'
    })
    .state('doradaRada', {
        url: '/doradaRada/{token}',
        templateUrl : 'appParts/doradaRada/doradaRada.html',
        controller : 'doradaRadaController'
    })
    .state('pregledIzmena', {
        url: '/pregledIzmena/{token}',
        templateUrl : 'appParts/pregledIzmena/pregledIzmena.html',
        controller : 'pregledIzmenaController'
    })
    .state('konacniUpload', {
        url: '/konacniUpload/{token}',
        templateUrl : 'appParts/konacniUpload/konacniUpload.html',
        controller : 'konacniUploadController'
    })
  });
