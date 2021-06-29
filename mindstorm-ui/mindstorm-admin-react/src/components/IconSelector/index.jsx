import React from 'react'
import * as Icons from "@ant-design/icons"
import { Modal, Tabs } from "antd"
import IconMaps from './icons'
import './index.less'

const { TabPane } = Tabs

const IconSelector = props => {
  const { menuForm, iconSelectSetting, setIconSelectSetting } = props

  return (
    <Modal
      visible={ iconSelectSetting.visible }
      onCancel={ () => setIconSelectSetting({ ...iconSelectSetting, visible: false }) }
      title="选择图标"
      width={ 800 }
      destroyOnClose={ true }
    >
      <Tabs defaultActiveKey="1">
        {
          IconMaps.map(item => {
            return (
              <TabPane tab={ item.title } key={ item.key }>
                <ul className="icon-list">
                  {
                    item.icons.map(icon => {
                      return (
                        <li key={ icon } style={ { fontSize: 36 } }
                            onClick={
                              () => {
                                menuForm.setFieldsValue({ icon })
                                setIconSelectSetting({
                                  visible: false,
                                  iconName: icon,
                                  icon: React.createElement(Icons[icon])
                                })
                              }
                            }
                        >
                          { React.createElement(Icons[icon]) }
                        </li>
                      )
                    })
                  }
                </ul>
              </TabPane>
            )
          })
        }
      </Tabs>
    </Modal>
  )
}

export default IconSelector
