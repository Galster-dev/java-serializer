/*
 * Copyright (C) 2023 Elytrium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.elytrium.serializer.language.writer;

import java.io.BufferedWriter;
import net.elytrium.serializer.SerializerConfig;
import net.elytrium.serializer.annotations.Comment;

public class JsonWriter extends YamlWriter {

  private String singleIndent = "  ";
  private String currentIndent = "";

  public JsonWriter(BufferedWriter writer, SerializerConfig config) {
    super(config, writer);
  }

  public JsonWriter(BufferedWriter writer) {
    super(writer);
  }

  public void setSingleIndent(String singleIndent) {
    this.singleIndent = singleIndent;
  }

  @Override
  public void writeCommentStart(Comment.At at) {
    synchronized (this) {
      if (at != Comment.At.SAME_LINE) {
        this.writeIndent();
      }

      this.writeRaw("//");
    }
  }

  @Override
  public void writeCommentEnd(Comment.At at) {
    synchronized (this) {
      if (at != Comment.At.SAME_LINE) {
        this.writeLine();
      }
    }
  }

  @Override
  public void writeNodeName(String nodeName) {
    synchronized (this) {
      this.writeIndent();
      this.writeRaw('"');
      this.writeRaw(nodeName);
      this.writeRaw("\": ");
    }
  }

  @Override
  public void writeBeginMap() {
    synchronized (this) {
      this.addIndent();
      this.writeRaw('{');
      this.writeLine();
    }
  }

  @Override
  public void writeMapPreCommentEntryJoin() {
    synchronized (this) {
      this.writeRaw(',');
    }
  }

  @Override
  public void writeMapPostCommentEntryJoin() {

  }

  @Override
  public void writeMapEntryEnd() {
    synchronized (this) {
      this.writeLine();
    }
  }

  @Override
  public void writeEndMap() {
    synchronized (this) {
      this.removeIndent();
      this.writeIndent();
      this.writeRaw('}');
    }
  }

  @Override
  public void writeBeginList() {
    synchronized (this) {
      this.addIndent();
      this.writeRaw('[');
      this.writeLine();
    }
  }

  @Override
  public void writeListEntry(Object entry) {
    synchronized (this) {
      this.writeIndent();
      this.writeNode(entry, null);
    }
  }

  @Override
  public void writeListEntryJoin() {
    synchronized (this) {
      this.writeRaw(',');
    }
  }

  @Override
  public void writeListEntryEnd() {
    synchronized (this) {
      this.writeLine();
    }
  }

  @Override
  public void writeEndList() {
    synchronized (this) {
      this.removeIndent();
      this.writeIndent();
      this.writeRaw(']');
    }
  }

  @Override
  public void writeLine() {
    synchronized (this) {
      super.writeRaw(this.config.getLineSeparator());
    }
  }

  private void writeIndent() {
    this.writeRaw(this.currentIndent);
  }

  private void addIndent() {
    this.currentIndent += this.singleIndent;
  }

  private void removeIndent() {
    this.currentIndent = this.currentIndent.substring(this.singleIndent.length());
  }
}
