(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('porra', {
            parent: 'entity',
            url: '/porra',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.porra.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porra/porras.html',
                    controller: 'PorraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('porra');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('porra-detail', {
            parent: 'entity',
            url: '/porra/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.porra.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porra/porra-detail.html',
                    controller: 'PorraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('porra');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Porra', function($stateParams, Porra) {
                    return Porra.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'porra',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('porra-detail.edit', {
            parent: 'porra-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porra/porra-dialog.html',
                    controller: 'PorraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Porra', function(Porra) {
                            return Porra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porra.new', {
            parent: 'porra',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porra/porra-dialog.html',
                    controller: 'PorraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cantidad: null,
                                eleccion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('porra', null, { reload: 'porra' });
                }, function() {
                    $state.go('porra');
                });
            }]
        })
        .state('porra.edit', {
            parent: 'porra',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porra/porra-dialog.html',
                    controller: 'PorraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Porra', function(Porra) {
                            return Porra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porra', null, { reload: 'porra' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porra.delete', {
            parent: 'porra',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porra/porra-delete-dialog.html',
                    controller: 'PorraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Porra', function(Porra) {
                            return Porra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porra', null, { reload: 'porra' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
