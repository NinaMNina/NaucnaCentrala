(function () {
    'use strict';

    angular
		.module('app')
		.controller('prijaviRadController', prijaviRadController);

    prijaviRadController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', 'jwtHelper', '$sce'];
        function prijaviRadController( $scope, $window, $localStorage, $location, $stateParams, $http, jwtHelper, $sce) {
            $scope.token = $stateParams.token;
            $scope.poruka="";
            var init = function(){
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/casopis/getAll'
                  }).then(function successCallback(response){
                	  if(response.data!="" || response.data!=null || response.data!=undefined){
                		  $scope.casopisi = response.data;
                	  }
                  },
                    function errorCallback(response){
                        alert("Greska");
                    });
            };
            init();
            
            $scope.odaberi = function(c){
            	var tokenData = $window.localStorage.getItem('token');
            	if(tokenData==null){
            		$window.localStorage.setItem('odabranCasopisId', c.id);
                	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                	return;
            	}
            	var taskId = $window.localStorage.getItem('taskId');          
          	  	if(taskId==undefined)
          		  taskId = "nemaga";
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/casopis/odaberi/'+taskId+'/'+c.id+'/'+$window.localStorage.getItem('token')
                  }).then(function successCallback(response){
                	  if(response.data==null){
                  		$window.localStorage.setItem('odabranCasopisId', c.id);
                      	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                	  }
                	  else 
                		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/zadaci/'+$window.localStorage.getItem('token');
                		  
                  },
                    function errorCallback(response){
                        
                   });
            }

            $scope.trustDangerousSnippet = function(tekstRada){
            	return $sce.trustAsHtml(tekstRada);
            }
           
        }
})();