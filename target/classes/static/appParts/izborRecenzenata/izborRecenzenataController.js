(function () {
    'use strict';

    angular
		.module('app')
		.controller('izborRecenzentaController', izborRecenzentaController);

    izborRecenzentaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function izborRecenzentaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
            $scope.token = $stateParams.token;
        	var odabrani = [];
        	$scope.porukica = "";
        	$scope.secretMessage = "";
        	var odabraniZadatakTaskId = "";
        	var zad = {};
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/izborRecenzenta/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
	        	}).then(function successCallback(response){
	        		$scope.recenzenti = response.data;
                },
                  function errorCallback(response){
                  });
            }
            $scope.home = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/home');
            }
            $scope.menjaStanje = function(id){
            	if(odabrani.includes(id)){
            		var ind = odabrani.indexOf(id);
            		odabrani.splice(ind, 1);
            		return;
            	}
            	odabrani.push(id);
            }          
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            }

            $scope.zavrsiZadatak = function(){
            	if(!(rok.dana>0 || rok.sati>0 || rok.minuta>0)){
            		alert("Niste uneli rok za recenziju");
            	}            
            	var data = [];
            	for(var i=0; i<$scope.recenzenti.length; i++){
            		if(odabrani.includes($scope.recenzenti[i].id)){
            			data.push($scope.recenzenti[i]);
            		}
            	}
            	var rok = "PT";
            	if(rok.dana>0){
            		rok=rok+rok.dana+"D";
            	}
            	if(rok.sati>0){
            		rok=rok+rok.sati+"H";
            	}
            	if(rok.minuta>0){
            		rok=rok+rok.minuta+"M";
            	}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/izborRecenzenta/reseno/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+rok,
                    data: data
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
        
        }
})();