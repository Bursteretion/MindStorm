import React, { useRef } from 'react';
import { Modal } from 'antd';
import { uploadQuestionImage } from '@/services/attachment';
import { Editor } from '@tinymce/tinymce-react';

const TinyMceModalEditor = (props) => {
  const { isModalVisible, setModalVisible, currentForm, targetInputName, currentValue } = props;
  const editorRef = useRef(null);

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText="确定"
        destroyOnClose={true}
        maskClosable={false}
        width={700}
        title="编辑"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          const obj = { targetInputName: editorRef?.current.getContent() };
          const keys = { targetInputName };
          const res = Object.keys(obj).reduce((newData, key) => {
            const newKey = keys[key] || key;
            newData[newKey] = obj[key];
            return newData;
          }, {});
          currentForm.setFieldsValue(res);
          setModalVisible(false);
        }}
      >
        <Editor
          initialValue={currentValue}
          onInit={(evt, editor) => (editorRef.current = editor)}
          apiKey="nzmlokkyb5avilcxl9sdfudwrnb818m5ovuc4c7av96gwue9"
          init={{
            language: 'zh_CN',
            min_height: 400,
            plugins:
              'preview searchreplace autolink directionality visualblocks visualchars fullscreen image link template code codesample table charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount imagetools textpattern help emoticons autosave autoresize formatpainter',
            toolbar:
              'code undo redo restoredraft |' +
              ' cut copy paste pastetext | ' +
              'forecolor backcolor bold italic underline strikethrough link anchor |' +
              ' alignleft aligncenter alignright alignjustify outdent indent |' +
              ' styleselect formatselect fontselect fontsizeselect |' +
              ' bullist numlist | blockquote subscript superscript removeformat | ' +
              'table image media charmap emoticons hr pagebreak insertdatetime print preview | ' +
              'fullscreen | bdmap indent2em lineheight formatpainter axupimgs',
            fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
            images_upload_handler: async (blobInfo, successFun, failFun) => {
              const file = blobInfo.blob();
              const formData = new FormData();
              formData.append('image', file);
              const res = await uploadQuestionImage(formData);
              if (res.success) {
                const { questionAttachment } = res.data;
                successFun(questionAttachment.path);
              } else {
                failFun(res.message);
              }
            },
          }}
        />
      </Modal>
    </>
  );
};

export default TinyMceModalEditor;
