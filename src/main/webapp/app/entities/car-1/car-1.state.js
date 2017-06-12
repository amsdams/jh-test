(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-1', {
            parent: 'entity',
            url: '/car-1',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car1S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-1/car-1-s.html',
                    controller: 'Car1Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('car-1-detail', {
            parent: 'car-1',
            url: '/car-1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car1'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-1/car-1-detail.html',
                    controller: 'Car1DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Car1', function($stateParams, Car1) {
                    return Car1.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-1',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-1-detail.edit', {
            parent: 'car-1-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-1/car-1-dialog.html',
                    controller: 'Car1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car1', function(Car1) {
                            return Car1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-1.new', {
            parent: 'car-1',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-1/car-1-dialog.html',
                    controller: 'Car1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-1', null, { reload: 'car-1' });
                }, function() {
                    $state.go('car-1');
                });
            }]
        })
        .state('car-1.edit', {
            parent: 'car-1',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-1/car-1-dialog.html',
                    controller: 'Car1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car1', function(Car1) {
                            return Car1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-1', null, { reload: 'car-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-1.delete', {
            parent: 'car-1',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-1/car-1-delete-dialog.html',
                    controller: 'Car1DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car1', function(Car1) {
                            return Car1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-1', null, { reload: 'car-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
