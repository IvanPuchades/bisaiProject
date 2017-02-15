(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('administrador-equipo', {
            parent: 'entity',
            url: '/administrador-equipo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.administradorEquipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipos.html',
                    controller: 'AdministradorEquipoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('administradorEquipo');
                    $translatePartialLoader.addPart('permiso');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('administrador-equipo-detail', {
            parent: 'entity',
            url: '/administrador-equipo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.administradorEquipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipo-detail.html',
                    controller: 'AdministradorEquipoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('administradorEquipo');
                    $translatePartialLoader.addPart('permiso');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AdministradorEquipo', function($stateParams, AdministradorEquipo) {
                    return AdministradorEquipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'administrador-equipo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('administrador-equipo-detail.edit', {
            parent: 'administrador-equipo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipo-dialog.html',
                    controller: 'AdministradorEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdministradorEquipo', function(AdministradorEquipo) {
                            return AdministradorEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('administrador-equipo.new', {
            parent: 'administrador-equipo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipo-dialog.html',
                    controller: 'AdministradorEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hora: null,
                                permiso: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('administrador-equipo', null, { reload: 'administrador-equipo' });
                }, function() {
                    $state.go('administrador-equipo');
                });
            }]
        })
        .state('administrador-equipo.edit', {
            parent: 'administrador-equipo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipo-dialog.html',
                    controller: 'AdministradorEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdministradorEquipo', function(AdministradorEquipo) {
                            return AdministradorEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('administrador-equipo', null, { reload: 'administrador-equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('administrador-equipo.delete', {
            parent: 'administrador-equipo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/administrador-equipo/administrador-equipo-delete-dialog.html',
                    controller: 'AdministradorEquipoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AdministradorEquipo', function(AdministradorEquipo) {
                            return AdministradorEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('administrador-equipo', null, { reload: 'administrador-equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
