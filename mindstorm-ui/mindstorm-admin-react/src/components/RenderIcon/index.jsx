import React from "react";
import * as Icons from "@ant-design/icons";

const RenderIcon = props => {
  const { iconName } = props
  return (
    React.createElement(Icons[iconName])
  )
}

export default RenderIcon
