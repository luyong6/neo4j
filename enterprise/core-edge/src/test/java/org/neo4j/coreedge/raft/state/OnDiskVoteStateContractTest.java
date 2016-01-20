/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.coreedge.raft.state;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import org.junit.Rule;

import org.neo4j.coreedge.raft.state.vote.OnDiskVoteState;
import org.neo4j.coreedge.raft.state.vote.VoteState;
import org.neo4j.coreedge.server.CoreMember;
import org.neo4j.io.fs.DefaultFileSystemAbstraction;
import org.neo4j.io.fs.FileSystemAbstraction;
import org.neo4j.test.TargetDirectory;

import static org.mockito.Mockito.mock;

public class OnDiskVoteStateContractTest extends VoteStateContractTest
{
    @Rule
    public final TargetDirectory.TestDirectory testDirectory = TargetDirectory.testDirForTest( getClass() );

    @Override
    public VoteState<CoreMember> createVoteStore()
    {
        FileSystemAbstraction fileSystem = new DefaultFileSystemAbstraction();
        File directory = testDirectory.directory( "raft-log" );
        try
        {
            return new OnDiskVoteState<>( fileSystem, directory, 100, mock( Supplier.class ), new CoreMember.CoreMemberMarshal() );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
}