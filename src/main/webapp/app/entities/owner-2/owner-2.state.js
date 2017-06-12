(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-2', {
            parent: 'entity',
            url: '/owner-2',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner2S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-2/owner-2-s.html',
                    controller: 'Owner2Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('owner-2-detail', {
            parent: 'owner-2',
            url: '/owner-2/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner2'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-2/owner-2-detail.html',
                    controller: 'Owner2DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner2', function($stateParams, Owner2) {
                    return Owner2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-2',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-2-detail.edit', {
            parent: 'owner-2-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-2/owner-2-dialog.html',
                    controller: 'Owner2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner2', function(Owner2) {
                            return Owner2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-2.new', {
            parent: 'owner-2',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-2/owner-2-dialog.html',
                    controller: 'Owner2DialogController',
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
                    $state.go('owner-2', null, { reload: 'owner-2' });
                }, function() {
                    $state.go('owner-2');
                });
            }]
        })
        .state('owner-2.edit', {
            parent: 'owner-2',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-2/owner-2-dialog.html',
                    controller: 'Owner2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner2', function(Owner2) {
                            return Owner2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-2', null, { reload: 'owner-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-2.delete', {
            parent: 'owner-2',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-2/owner-2-delete-dialog.html',
                    controller: 'Owner2DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner2', function(Owner2) {
                            return Owner2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-2', null, { reload: 'owner-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
