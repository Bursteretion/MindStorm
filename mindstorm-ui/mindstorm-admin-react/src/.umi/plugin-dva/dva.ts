// @ts-nocheck
import { Component } from 'react';
import { ApplyPluginsType } from 'umi';
import dva from 'dva';
// @ts-ignore
import createLoading from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/dva-loading/dist/index.esm.js';
import { plugin, history } from '../core/umiExports';
import ModelAuth0 from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/models/auth.js';
import ModelGlobal1 from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/models/global.js';
import ModelMenu2 from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/models/menu.js';
import ModelSetting3 from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/models/setting.js';
import ModelUser4 from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/models/user.js';

let app:any = null;

export function _onCreate(options = {}) {
  const runtimeDva = plugin.applyPlugins({
    key: 'dva',
    type: ApplyPluginsType.modify,
    initialValue: {},
  });
  app = dva({
    history,
    
    ...(runtimeDva.config || {}),
    // @ts-ignore
    ...(typeof window !== 'undefined' && window.g_useSSR ? { initialState: window.g_initialProps } : {}),
    ...(options || {}),
  });
  
  app.use(createLoading());
  (runtimeDva.plugins || []).forEach((plugin:any) => {
    app.use(plugin);
  });
  app.model({ namespace: 'auth', ...ModelAuth0 });
app.model({ namespace: 'global', ...ModelGlobal1 });
app.model({ namespace: 'menu', ...ModelMenu2 });
app.model({ namespace: 'setting', ...ModelSetting3 });
app.model({ namespace: 'user', ...ModelUser4 });
  return app;
}

export function getApp() {
  return app;
}

/**
 * whether browser env
 * 
 * @returns boolean
 */
function isBrowser(): boolean {
  return typeof window !== 'undefined' &&
  typeof window.document !== 'undefined' &&
  typeof window.document.createElement !== 'undefined'
}

export class _DvaContainer extends Component {
  constructor(props: any) {
    super(props);
    // run only in client, avoid override server _onCreate()
    if (isBrowser()) {
      _onCreate()
    }
  }

  componentWillUnmount() {
    let app = getApp();
    app._models.forEach((model:any) => {
      app.unmodel(model.namespace);
    });
    app._models = [];
    try {
      // 释放 app，for gc
      // immer 场景 app 是 read-only 的，这里 try catch 一下
      app = null;
    } catch(e) {
      console.error(e);
    }
  }

  render() {
    let app = getApp();
    app.router(() => this.props.children);
    return app.start()();
  }
}
