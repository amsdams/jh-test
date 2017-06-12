(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-4', {
            parent: 'entity',
            url: '/owner-4',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner4S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-4/owner-4-s.html',
                    controller: 'Owner4Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('owner-4-detail', {
            parent: 'owner-4',
            url: '/owner-4/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner4'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-4/owner-4-detail.html',
                    controller: 'Owner4DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner4', function($stateParams, Owner4) {
                    return Owner4.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-4',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-4-detail.edit', {
            parent: 'owner-4-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-4/owner-4-dialog.html',
                    controller: 'Owner4DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner4', function(Owner4) {
                            return Owner4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-4.new', {
            parent: 'owner-4',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-4/owner-4-dialog.html',
                    controller: 'Owner4DialogController',
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
                    $state.go('owner-4', null, { reload: 'owner-4' });
                }, function() {
                    $state.go('owner-4');
                });
            }]
        })
        .state('owner-4.edit', {
            parent: 'owner-4',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-4/owner-4-dialog.html',
                    controller: 'Owner4DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner4', function(Owner4) {
                            return Owner4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-4', null, { reload: 'owner-4' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-4.delete', {
            parent: 'owner-4',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-4/owner-4-delete-dialog.html',
                    controller: 'Owner4DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner4', function(Owner4) {
                            return Owner4.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-4', null, { reload: 'owner-4' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
