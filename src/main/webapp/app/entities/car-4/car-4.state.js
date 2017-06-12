(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-4', {
            parent: 'entity',
            url: '/car-4',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car4S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-4/car-4-s.html',
                    controller: 'Car4Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('car-4-detail', {
            parent: 'car-4',
            url: '/car-4/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car4'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-4/car-4-detail.html',
                    controller: 'Car4DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Car4', function($stateParams, Car4) {
                    return Car4.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-4',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-4-detail.edit', {
            parent: 'car-4-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-4/car-4-dialog.html',
                    controller: 'Car4DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car4', function(Car4) {
                            return Car4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-4.new', {
            parent: 'car-4',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-4/car-4-dialog.html',
                    controller: 'Car4DialogController',
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
                    $state.go('car-4', null, { reload: 'car-4' });
                }, function() {
                    $state.go('car-4');
                });
            }]
        })
        .state('car-4.edit', {
            parent: 'car-4',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-4/car-4-dialog.html',
                    controller: 'Car4DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car4', function(Car4) {
                            return Car4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-4', null, { reload: 'car-4' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-4.delete', {
            parent: 'car-4',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-4/car-4-delete-dialog.html',
                    controller: 'Car4DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car4', function(Car4) {
                            return Car4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-4', null, { reload: 'car-4' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
