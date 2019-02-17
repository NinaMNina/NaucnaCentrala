(function () {
    'use strict';

    angular
		.module('app')
		.controller('pregledPDFaController', pregledPDFaController);

    pregledPDFaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function pregledPDFaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	$scope.odluka = {};
        	$scope.podaci = {};
            $scope.init = function(){
            	$scope.komentar="";
          
            }
            $scope.home = function(){
            	$window.localStorage.remove('taskIdOdZadaciObrisiOdmah');
            	$location.path('/home');
            }
    
            $scope.zavrsiZadatak = function(text){       
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPDFa/reseno/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+text,
                    data: {"key": "pp_komentarUradnika",
                    	"value": $scope.komentar,
                    	"tip": "STRING"
                    }
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                    	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home'             			          		  
                	  }
                  },
                    function errorCallback(response){
                        alert("Greška pri obavljanu zadatka");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                    });            	
            }
            $scope.otvoriRad = function(){       
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPDFa/getPDF/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
                  }).then(function successCallback(response){
                	  if(response!=null){
                		  var anchor = angular.element('<a/>');
                          anchor.attr({
                              href: 'data:attachment/pdf;charset=utf-8,' + encodeURI(response.data),
                              target: '_blank',
                              download: 'rad.pdf'
                          })[0].click();
                  	  }
                  },
                    function errorCallback(response){
                        
                    });            	
            }
            
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
            }
        }
})();