(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function homeController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	var hc=this;
            $scope.token = $stateParams.token;
            $scope.params = [];
            $scope.pretragaReci = "";
            $scope.searchResult = [];

            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	else{
            	/*	$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/login/checkValidity/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data==false)
                      		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });*/
            	}
            }
            $scope.dodajParametar = function(){
                $scope.params.push({"operacija": "",
                	"polje": "",
                	"vrednost": ""});
            }
            
            $scope.pretrazi = function(){
            	var p = $scope.params;
            	if(p=="" || p==undefined)
            		return;
            	var parametri = [];
            	for(var i=0; i<p.length; i++){
            		if(p[i].operacija=="" || p[i].operacija==undefined
            				|| p[i].polje=="" || p[i].polje==undefined
            				|| p[i].vrednost=="" || p[i].vrednost==undefined){
            			alert("Svi parametri pretrage moraju biti uneseni");
            			return;
            		}
            		var p0={"operacija": p[i].operacija,
                        	"polje": p[i].polje,
                        	"vrednost": p[i].vrednost}
            		parametri.push(p0);
            	}
            	var data = {"parametri": parametri}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/search',
                    data: data
                  }).then(function successCallback(response){
                	  $scope.searchResult = response.data;
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za pretragu");
                    });
            }
            $scope.obrisiParametar = function(parametar){
            	if($scope.params.length>=parametar)
            		$scope.params.splice(parametar, 1);
            }
            
            $scope.pretraziPoRecima = function(){
            	var data = $scope.pretragaReci;
            	if(data=="")
            		return;
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/search/'+data
                  }).then(function successCallback(response){
                	  $scope.searchResult = response.data;
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za pretragu");
                    });
            }
            $scope.obrisiSve = function(){
            	$scope.searchResult = [];
                $scope.params = [];
                $scope.pretragaReci = "";
            }
            $scope.pronadjiSlicne = function(result){
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/moreLikeThis/'+result
                  }).then(function successCallback(response){
                	  $scope.searchResult = response.data;
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za slične radove");
                    });
            }
            $scope.trustDangerousSnippet = function(tekstRada){
            	return $sce.trustAsHtml(tekstRada);
            }
        }
})();