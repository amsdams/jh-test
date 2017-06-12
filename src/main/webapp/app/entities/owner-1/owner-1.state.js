(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-1', {
            parent: 'entity',
            url: '/owner-1',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner1S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-1/owner-1-s.html',
                    controller: 'Owner1Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('owner-1-detail', {
            parent: 'owner-1',
            url: '/owner-1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner1'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-1/owner-1-detail.html',
                    controller: 'Owner1DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner1', function($stateParams, Owner1) {
                    return Owner1.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-1',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-1-detail.edit', {
            parent: 'owner-1-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-1/owner-1-dialog.html',
                    controller: 'Owner1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner1', function(Owner1) {
                            return Owner1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-1.new', {
            parent: 'owner-1',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-1/owner-1-dialog.html',
                    controller: 'Owner1DialogController',
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
                    $state.go('owner-1', null, { reload: 'owner-1' });
                }, function() {
                    $state.go('owner-1');
                });
            }]
        })
        .state('owner-1.edit', {
            parent: 'owner-1',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-1/owner-1-dialog.html',
                    controller: 'Owner1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner1', function(Owner1) {
                            return Owner1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-1', null, { reload: 'owner-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-1.delete', {
            parent: 'owner-1',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-1/owner-1-delete-dialog.html',
                    controller: 'Owner1DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner1', function(Owner1) {
                            return Owner1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-1', null, { reload: 'owner-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
