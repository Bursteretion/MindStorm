export default function access(initialState) {
  const { authorityRoutes, permissions } = initialState;
  return {
    hasRouter: (route) => authorityRoutes.filter((item) => item === route.name).length > 0,
  };
}
