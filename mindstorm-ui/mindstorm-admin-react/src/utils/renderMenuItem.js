import React from 'react';
import * as Icons from '@ant-design/icons';

const renderMenuItem = (menus = []) => {
  menus
    .sort((a, b) => a.sort - b.sort)
    .forEach((item) => {
      const { icon, children } = item;
      item.name = item.alias;
      if (icon !== undefined && icon !== '') {
        // eslint-disable-next-line no-param-reassign
        if (Icons[icon] !== undefined) {
          item.icon = React.createElement(Icons[icon]);
        } else {
          item.icon = undefined;
        }
      }
      // eslint-disable-next-line no-param-reassign,no-unused-expressions
      children && children.length > 0 ? (item.children = renderMenuItem(children)) : null;
    });
  return menus;
};

export default renderMenuItem;
