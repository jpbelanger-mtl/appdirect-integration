angular.module('appdirect-integration', ['ui.bootstrap', 'ui.grid', 'ngRoute'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/companies', {
            templateUrl: 'template/companies.html',
            controller: 'companiesController'
        }).
        when('/users', {
            templateUrl: 'template/users.html',
            controller: 'usersController'
        });
    }
])

.controller('mainController', function($scope, $http) {
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

	$http.get('/app/info').
        success(function(data, status, headers, config) {
            $scope.alerts = [{
		        type: 'success',
		        msg: 'Welcome ' + data.principal.username + " your current role is " + data.principal.authorities[0].authority,
		        timeout: "10000"
		    }];
        }).
        error(function(data, status, headers, config) {
            $scope.$emit('alert', 'danger', 'Failed to load data');
        });
        
    $scope.$on('alert', function(e, type, message) {
        $scope.alerts.push({
            type: type,
            msg: message,
            timeout: "10000"
        });
    });
})



.controller('companiesController', function($scope, $http, uiGridConstants) {
    $scope.message = 'This is companiesController screen';

    $scope.$scope = $scope;
    $scope.gridOptions = {
        enableFiltering: true,
        enableColumnResizing: true,
        columnDefs: [{
            field: 'name',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'email',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'edition',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'maxUsers',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'bandwidth',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'status',
            cellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
                var value = grid.getCellValue(row, col)
                if (value === 'ACTIVE') {
                    return 'bg-success';
                } else {
                    return 'bg-danger';
                }
            }
        }]
    }


    $scope.refresh = function() {
        $http.get('/app/companies').
        success(function(data, status, headers, config) {
            $scope.gridOptions.data = data;
        }).
        error(function(data, status, headers, config) {
            $scope.$emit('alert', 'danger', 'Failed to load data');
        })
    }

    $scope.refresh();
})

.controller('usersController', function($scope, $http, uiGridConstants) {
    $scope.message = 'This is usersController screen';

    $scope.$scope = $scope;
    $scope.gridOptions = {
        enableFiltering: true,
        enableColumnResizing: true,
        columnDefs: [{
            field: 'firstName',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'lastName',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'email',
            filter: {
                condition: uiGridConstants.filter.CONTAINS
            }
        }, {
            field: 'admin',
            cellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
                var value = grid.getCellValue(row, col)
                if (value === 'true') {
                    return 'bg-success';
                } else {
                    return 'bg-warning';
                }
            }
        }]
    }


    $scope.refresh = function() {
        $http.get('/app/users').
        success(function(data, status, headers, config) {
            $scope.gridOptions.data = data;
        }).
        error(function(data, status, headers, config) {
            $scope.$emit('alert', 'danger', 'Failed to load data');
        })
    }

    $scope.refresh();
})
