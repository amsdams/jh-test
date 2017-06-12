(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-3', {
            parent: 'entity',
            url: '/car-3',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car3S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-3/car-3-s.html',
                    controller: 'Car3Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('car-3-detail', {
            parent: 'car-3',
            url: '/car-3/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car3'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-3/car-3-detail.html',
                    controller: 'Car3DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Car3', function($stateParams, Car3) {
                    return Car3.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-3',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-3-detail.edit', {
            parent: 'car-3-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-3/car-3-dialog.html',
                    controller: 'Car3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car3', function(Car3) {
                            return Car3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-3.new', {
            parent: 'car-3',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-3/car-3-dialog.html',
                    controller: 'Car3DialogController',
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
                    $state.go('car-3', null, { reload: 'car-3' });
                }, function() {
                    $state.go('car-3');
                });
            }]
        })
        .state('car-3.edit', {
            parent: 'car-3',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-3/car-3-dialog.html',
                    controller: 'Car3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car3', function(Car3) {
                            return Car3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-3', null, { reload: 'car-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-3.delete', {
            parent: 'car-3',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-3/car-3-delete-dialog.html',
                    controller: 'Car3DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car3', function(Car3) {
                            return Car3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-3', null, { reload: 'car-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
