export default function access(initialState) {
  const { authorityRoutes = [] } = initialState;
  return {
    hasRouter: (route) => authorityRoutes.filter((item) => item === route.name).length > 0,
  };
}
