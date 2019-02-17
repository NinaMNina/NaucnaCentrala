(function () {
    'use strict';

    angular
		.module('app')
		.controller('pregledPodatakaController', pregledPodatakaController);

    pregledPodatakaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function pregledPodatakaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	$scope.odluka = {};
        	$scope.podaci = {};
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPodataka/podaci/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+$stateParams.token
	        	}).then(function successCallback(response){
	        		$scope.podaci = response.data;
                },
                  function errorCallback(response){
                      //alert("Problem pri dobavljanju naucnih oblasti. Neuspešno obavljeno!")
                  });
          
            }
            $scope.home = function(){
            	$window.localStorage.remove('taskIdOdZadaciObrisiOdmah');
            	$location.path('/home');
            }
    
            $scope.zadatakRelevantan = function(){       
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPodataka/reseno/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/prihvaceno'
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
            $scope.zadatakNijeRelevantan = function(){       
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPodataka/reseno/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/odbijeno'
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                      	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
                      	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home'             			          		  
                  	  }
                  },
                    function errorCallback(response){
                        alert("Greška pri obavljanju zadatka");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                    });            	
            }
            
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
            }
        }
})();