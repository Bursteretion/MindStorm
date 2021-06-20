import RenderAuthorize from '@/components/Authorized';
import { getToken } from './authority';

let Authorized = RenderAuthorize(getToken()); // Reload the rights component

const reloadAuthorized = () => {
  Authorized = RenderAuthorize(getToken());
};

window.reloadAuthorized = reloadAuthorized;
export { reloadAuthorized };
export default Authorized;
