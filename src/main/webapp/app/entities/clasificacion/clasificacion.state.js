(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('clasificacion', {
            parent: 'entity',
            url: '/clasificacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.clasificacion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clasificacion/clasificacions.html',
                    controller: 'ClasificacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clasificacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('clasificacion-detail', {
            parent: 'entity',
            url: '/clasificacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.clasificacion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clasificacion/clasificacion-detail.html',
                    controller: 'ClasificacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clasificacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Clasificacion', function($stateParams, Clasificacion) {
                    return Clasificacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'clasificacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('clasificacion-detail.edit', {
            parent: 'clasificacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clasificacion/clasificacion-dialog.html',
                    controller: 'ClasificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clasificacion', function(Clasificacion) {
                            return Clasificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clasificacion.new', {
            parent: 'clasificacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clasificacion/clasificacion-dialog.html',
                    controller: 'ClasificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                url: null,
                                resultado: null,
                                foto: null,
                                fotoContentType: null,
                                ranking: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('clasificacion', null, { reload: 'clasificacion' });
                }, function() {
                    $state.go('clasificacion');
                });
            }]
        })
        .state('clasificacion.edit', {
            parent: 'clasificacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clasificacion/clasificacion-dialog.html',
                    controller: 'ClasificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clasificacion', function(Clasificacion) {
                            return Clasificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clasificacion', null, { reload: 'clasificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clasificacion.delete', {
            parent: 'clasificacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clasificacion/clasificacion-delete-dialog.html',
                    controller: 'ClasificacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Clasificacion', function(Clasificacion) {
                            return Clasificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clasificacion', null, { reload: 'clasificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
