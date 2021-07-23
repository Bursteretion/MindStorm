import React, { useRef } from 'react';
import { FrownOutlined } from '@ant-design/icons';
import { Drawer } from 'antd';
import { Editor } from '@tinymce/tinymce-react';
import styles from '@/pages/Course/manager/style.less';

const QuestionDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId, actionRef, userId } = props;

  const editorRef = useRef(null);
  const log = () => {
    if (editorRef.current) {
      console.log(editorRef.current.getContent());
    }
  };

  return (
    <Drawer
      destroyOnClose
      getContainer={false}
      className={styles.drawer}
      title={
        <span>
          <FrownOutlined style={{ marginRight: 10 }} />
          新增题目
        </span>
      }
      width="100%"
      height="100%"
      placement="bottom"
      onClose={() => {
        setDrawerVisible(false);
        actionRef?.current.reset();
      }}
      visible={isDrawerVisible}
    >
      <Editor
        onInit={(evt, editor) => (editorRef.current = editor)}
        initialValue="<p>This is the initial content of the editor.</p>"
        apiKey="nzmlokkyb5avilcxl9sdfudwrnb818m5ovuc4c7av96gwue9"
        init={{
          height: 500,
          language: 'zh_CN',
          tinycomments_mode: 'embedded',
          tinycomments_author: '罗炜杰',
          plugins: [
            'advlist autolink lists link image charmap print preview anchor',
            'searchreplace visualblocks code fullscreen',
            'insertdatetime media table paste code help wordcount',
          ],
          toolbar:
            'code undo redo restoredraft | cut copy paste pastetext | ' +
            'forecolor backcolor bold italic underline strikethrough link anchor | ' +
            'alignleft aligncenter alignright alignjustify outdent indent | ' +
            'styleselect formatselect fontselect fontsizeselect | bullist numlist | ' +
            'blockquote subscript superscript removeformat | ' +
            'table image media charmap emoticons hr pagebreak insertdatetime print preview | ' +
            'fullscreen | bdmap indent2em lineheight formatpainter axupimgs',
          fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
          content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
        }}
      />
      <button onClick={log}>Log editor content</button>
    </Drawer>
  );
};

export default QuestionDrawer;
